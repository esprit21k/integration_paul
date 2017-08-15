import { call, put, takeLatest } from 'redux-saga/effects';
import { handleApiErrors } from '../lib/api-errors';
import {
  SIGNUP_REQUESTING,
  SIGNUP_SUCCESS,
  SIGNUP_ERROR,
 } from './constants';

const signupUrl = `${process.env.REACT_APP_API_URL}/signup`;

function signupApi(username, email, password) {
  return fetch(signupUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, email, password }),
  })
    .then(response => response)
    .then(response => response.json())
    .then(json => json)
    .then(handleApiErrors)
  .catch((error) => { throw error; });
}
function* signupFlow(action) {
  try {
    const { username, email, password } = action;
    const response = yield call(signupApi, username, email, password);

    yield put({ type: SIGNUP_SUCCESS, response });

  } catch (error) {
    yield put({ type: SIGNUP_ERROR, error });
  }
}

function* signupWatcher() {
  yield takeLatest(SIGNUP_REQUESTING, signupFlow);
}

export default signupWatcher;
