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

import { getEntities, reset } from './patient-test-timings.reducer';

export const PatientTestTimings = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const patientTestTimingsList = useAppSelector(state => state.patientTestTimings.entities);
  const loading = useAppSelector(state => state.patientTestTimings.loading);
  const links = useAppSelector(state => state.patientTestTimings.links);
  const updateSuccess = useAppSelector(state => state.patientTestTimings.updateSuccess);

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
      <h2 id="patient-test-timings-heading" data-cy="PatientTestTimingsHeading">
        Patient Test Timings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/patient-test-timings/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Patient Test Timings
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={patientTestTimingsList ? patientTestTimingsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {patientTestTimingsList && patientTestTimingsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('priority')}>
                    Priority <FontAwesomeIcon icon={getSortIconByFieldName('priority')} />
                  </th>
                  <th className="hand" onClick={sort('clinicalNote')}>
                    Clinical Note <FontAwesomeIcon icon={getSortIconByFieldName('clinicalNote')} />
                  </th>
                  <th className="hand" onClick={sort('spclInstruction')}>
                    Spcl Instruction <FontAwesomeIcon icon={getSortIconByFieldName('spclInstruction')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    Start Timing <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    End Time <FontAwesomeIcon icon={getSortIconByFieldName('endTime')} />
                  </th>
                  <th>
                    Patient Info <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Test Categories <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {patientTestTimingsList.map((patientTestTimings, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/patient-test-timings/${patientTestTimings.id}`} color="link" size="sm">
                        {patientTestTimings.id}
                      </Button>
                    </td>
                    <td>{patientTestTimings.priority}</td>
                    <td>{patientTestTimings.clinicalNote}</td>
                    <td>{patientTestTimings.spclInstruction}</td>
                    <td>{patientTestTimings.status}</td>
                    <td>
                      {patientTestTimings.startTime ? (
                        <TextFormat type="date" value={patientTestTimings.startTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {patientTestTimings.endTime ? (
                        <TextFormat type="date" value={patientTestTimings.endTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {patientTestTimings.patientInfo ? (
                        <Link to={`/patient-info/${patientTestTimings.patientInfo.id}`}>{patientTestTimings.patientInfo.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {patientTestTimings.testCategories ? (
                        <Link to={`/test-categories/${patientTestTimings.testCategories.id}`}>{patientTestTimings.testCategories.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/patient-test-timings/${patientTestTimings.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/patient-test-timings/${patientTestTimings.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/patient-test-timings/${patientTestTimings.id}/delete`)}
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
            !loading && <div className="alert alert-warning">No Patient Test Timings found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PatientTestTimings;
