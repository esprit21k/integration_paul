import { call, put, takeLatest } from 'redux-saga/effects';
import { handleApiErrors } from '../lib/api-errors';
import { hashHistory } from 'react-router';
import {
  WIDGET_CREATING,
} from './constants';

import {
  widgetCreateSuccess,
  widgetCreateError,
} from './actions';

import {
  unsetClient,
} from '../client/actions';

const widgetsUrl = `${process.env.REACT_APP_API_URL}`;

function widgetCreateApi(client, widget) {
  console.log(client);
  const url = `${widgetsUrl}/test`;
  return fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: client.token || undefined, // will throw an error if no login
    },
    body: JSON.stringify(widget),
  })
  .then(handleApiErrors)
  .then(response => response.json())
  .then(json => json)
  .catch((error) => { throw error; });
}

function* widgetCreateFlow(action) {
  try {
    const { client, widget } = action;
    const createdWidget = yield call(widgetCreateApi, client, widget);
    yield put(widgetCreateSuccess(createdWidget));
  } catch (error) {
    // same with error
    if (error.search('Expired') > -1) {
      yield put({ type: 'LOGOUT_REQUESTING' });
      hashHistory.push('/#/login');
    } else {
      yield put(widgetCreateError(error));
    }
  }
}

function* widgetsWatcher() {
  yield [
    takeLatest(WIDGET_CREATING, widgetCreateFlow),
  ];
}

export default widgetsWatcher;
