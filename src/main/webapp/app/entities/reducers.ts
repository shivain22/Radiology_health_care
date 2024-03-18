import employee from 'app/entities/employee/employee.reducer';
import unit from 'app/entities/unit/unit.reducer';
import services from 'app/entities/services/services.reducer';
import rank from 'app/entities/rank/rank.reducer';
import patientInfo from 'app/entities/patient-info/patient-info.reducer';
import rooms from 'app/entities/rooms/rooms.reducer';
import equipments from 'app/entities/equipments/equipments.reducer';
import testCatogories from 'app/entities/test-catogories/test-catogories.reducer';
import testTimings from 'app/entities/test-timings/test-timings.reducer';
import patientTestInfo from 'app/entities/patient-test-info/patient-test-info.reducer';
import techicianEquipmentMapping from 'app/entities/techician-equipment-mapping/techician-equipment-mapping.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  employee,
  unit,
  services,
  rank,
  patientInfo,
  rooms,
  equipments,
  testCatogories,
  testTimings,
  patientTestInfo,
  techicianEquipmentMapping,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
