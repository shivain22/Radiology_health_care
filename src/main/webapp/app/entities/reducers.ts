import room from 'app/entities/room/room.reducer';
import empService from 'app/entities/emp-service/emp-service.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
import rank from 'app/entities/rank/rank.reducer';
import unit from 'app/entities/unit/unit.reducer';
import employee from 'app/entities/employee/employee.reducer';
import technicianEquipmentMapping from 'app/entities/technician-equipment-mapping/technician-equipment-mapping.reducer';
import patientInfo from 'app/entities/patient-info/patient-info.reducer';
import patientTestTimings from 'app/entities/patient-test-timings/patient-test-timings.reducer';
import testCategories from 'app/entities/test-categories/test-categories.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  room,
  empService,
  equipment,
  rank,
  unit,
  employee,
  technicianEquipmentMapping,
  patientInfo,
  patientTestTimings,
  testCategories,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
