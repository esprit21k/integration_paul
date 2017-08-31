import { hashHistory } from 'react-router';
import { take, call, put } from 'redux-saga/effects';
import {
  SIGNIN_REDIRECT,
  SIGNOUT
} from './constants';

function redirectFlow() {
  hashHistory.replace('/#/signup');
}

function* topbarWatcher() {
  while (true) {
    const action = yield take([SIGNIN_REDIRECT, SIGNOUT]);
    if (action.type === SIGNIN_REDIRECT) {
      yield call(redirectFlow);
    }
    if (action.type === SIGNOUT) {
      yield put({ type: 'LOGOUT_REQUESTING' });
    }
  }
}

export default topbarWatcher;
