import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/room">
        Room
      </MenuItem>
      <MenuItem icon="asterisk" to="/emp-service">
        Emp Service
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment">
        Equipment
      </MenuItem>
      <MenuItem icon="asterisk" to="/rank">
        Rank
      </MenuItem>
      <MenuItem icon="asterisk" to="/unit">
        Unit
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="asterisk" to="/technician-equipment-mapping">
        Technician Equipment Mapping
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient-info">
        Patient Info
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient-test-timings">
        Patient Test Timings
      </MenuItem>
      <MenuItem icon="asterisk" to="/test-categories">
        Test Categories
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
