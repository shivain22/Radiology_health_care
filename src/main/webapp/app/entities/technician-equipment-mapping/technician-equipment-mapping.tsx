import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './technician-equipment-mapping.reducer';

export const TechnicianEquipmentMapping = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const technicianEquipmentMappingList = useAppSelector(state => state.technicianEquipmentMapping.entities);
  const loading = useAppSelector(state => state.technicianEquipmentMapping.loading);
  const links = useAppSelector(state => state.technicianEquipmentMapping.links);
  const updateSuccess = useAppSelector(state => state.technicianEquipmentMapping.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="technician-equipment-mapping-heading" data-cy="TechnicianEquipmentMappingHeading">
        Technician Equipment Mappings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/technician-equipment-mapping/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Technician Equipment Mapping
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={technicianEquipmentMappingList ? technicianEquipmentMappingList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {technicianEquipmentMappingList && technicianEquipmentMappingList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('dateTime')}>
                    Date Time <FontAwesomeIcon icon={getSortIconByFieldName('dateTime')} />
                  </th>
                  <th>
                    Equipment <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Employee <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    User <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {technicianEquipmentMappingList.map((technicianEquipmentMapping, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/technician-equipment-mapping/${technicianEquipmentMapping.id}`} color="link" size="sm">
                        {technicianEquipmentMapping.id}
                      </Button>
                    </td>
                    <td>
                      {technicianEquipmentMapping.dateTime ? (
                        <TextFormat type="date" value={technicianEquipmentMapping.dateTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {technicianEquipmentMapping.equipment ? (
                        <Link to={`/equipment/${technicianEquipmentMapping.equipment.id}`}>{technicianEquipmentMapping.equipment.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {technicianEquipmentMapping.employee ? (
                        <Link to={`/employee/${technicianEquipmentMapping.employee.id}`}>{technicianEquipmentMapping.employee.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{technicianEquipmentMapping.user ? technicianEquipmentMapping.user.id : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/technician-equipment-mapping/${technicianEquipmentMapping.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/technician-equipment-mapping/${technicianEquipmentMapping.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/technician-equipment-mapping/${technicianEquipmentMapping.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Technician Equipment Mappings found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default TechnicianEquipmentMapping;
