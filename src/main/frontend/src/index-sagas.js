import { all } from 'redux-saga/effects';
import SignupSaga from './signup/sagas';
import LoginSaga from './login/sagas';
import WidgetSaga from './widgets/sagas';

export default function* IndexSaga() {
  yield all([
    SignupSaga(),
    LoginSaga(),
    WidgetSaga(),
  ]);
}
