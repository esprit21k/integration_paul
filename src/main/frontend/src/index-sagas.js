import { all } from 'redux-saga/effects';
import SignupSaga from './signup/sagas';
import LoginSaga from './login/sagas';
import WidgetSaga from './widgets/sagas';
import TopbarSaga from './topbar/sagas';
import ModalSaga from './ui/modal/sagas';

export default function* IndexSaga() {
  yield all([
    SignupSaga(),
    LoginSaga(),
    WidgetSaga(),
    TopbarSaga(),
    ModalSaga(),
  ]);
}
