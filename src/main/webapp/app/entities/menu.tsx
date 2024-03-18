import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/employee">
        Employee
      </MenuItem>
      <MenuItem icon="asterisk" to="/unit">
        Unit
      </MenuItem>
      <MenuItem icon="asterisk" to="/services">
        Services
      </MenuItem>
      <MenuItem icon="asterisk" to="/rank">
        Rank
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient-info">
        Patient Info
      </MenuItem>
      <MenuItem icon="asterisk" to="/rooms">
        Rooms
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipments">
        Equipments
      </MenuItem>
      <MenuItem icon="asterisk" to="/test-catogories">
        Test Catogories
      </MenuItem>
      <MenuItem icon="asterisk" to="/test-timings">
        Test Timings
      </MenuItem>
      <MenuItem icon="asterisk" to="/patient-test-info">
        Patient Test Info
      </MenuItem>
      <MenuItem icon="asterisk" to="/techician-equipment-mapping">
        Techician Equipment Mapping
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
