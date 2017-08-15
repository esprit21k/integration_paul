import { call, put, takeLatest } from 'redux-saga/effects';
import { handleApiErrors } from '../lib/api-errors';
import {
  WIDGET_CREATING,
} from './constants';

import {
  widgetCreateSuccess,
  widgetCreateError,
} from './actions';

const widgetsUrl = `${process.env.REACT_APP_API_URL}/api/Clients`;

function widgetCreateApi(client, widget) {
  const url = `${widgetsUrl}/${client.id}/widgets`;
  return fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: client.token.id || undefined, // will throw an error if no login
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
    yield put(widgetCreateError(error));
  }
}

function* widgetsWatcher() {
  yield [
    takeLatest(WIDGET_CREATING, widgetCreateFlow),
  ];
}

export default widgetsWatcher;
