import { take, fork, cancel, call, put, cancelled } from 'redux-saga/effects';

// We'll use this function to redirect to different routes based on cases
import { hashHistory } from 'react-router';

// Helper for api errors
import { handleApiErrors } from '../lib/api-errors';

// Our login constants
import {
  LOGIN_REQUESTING,
  LOGIN_SUCCESS,
  LOGIN_ERROR,
  LOGOUT_REQUESTING,
  LOGOUT_SUCCESS
} from './constants';

// So that we can modify our Client piece of state
import {
  setClient,
  unsetClient,
} from '../client/actions';

import {
  CLIENT_UNSET,
} from '../client/constants';

const loginUrl = `${process.env.REACT_APP_API_URL}/login`;

function loginApi(username, password) {
  return fetch(loginUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
  })
  .then(response => response)
  .then(response => response.json())
  .then(json => json)
  .then(handleApiErrors)
  .catch((error) => { throw error; });
}

function* logout() {
  yield put(unsetClient());

  localStorage.removeItem('token');

  hashHistory.push('/#/login');
}

function* loginFlow(username, password) {
  let resp;
  try {
    resp = yield call(loginApi, username, password);
    const tok = resp.data.token;
    yield put(setClient(tok));
    yield put({ type: LOGIN_SUCCESS });
    localStorage.setItem('token', JSON.stringify(tok));
    hashHistory.push('/#/dashboard');
  } catch (error) {
    const putBody = { type: LOGIN_ERROR, error };
    yield put(putBody);
  } finally {
    if (yield cancelled()) {
      hashHistory.push('/#/login');
    }
  }
  return resp;
}

function* loginWatcher() {
  while (true) {
    //
    const action = yield take([LOGIN_REQUESTING, CLIENT_UNSET, LOGIN_ERROR, LOGOUT_REQUESTING]);
    let task = null;
    if (action.type === LOGIN_REQUESTING) {
      task = yield fork(loginFlow, action.username, action.password);
    }
    if (action.type === CLIENT_UNSET) {
      yield cancel(task);
    }
    if (action.type === LOGOUT_REQUESTING) {
      yield put({ type: LOGOUT_SUCCESS });
    }
    yield call(logout);
  }
}

export default loginWatcher;
