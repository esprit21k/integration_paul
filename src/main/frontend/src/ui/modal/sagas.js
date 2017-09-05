import { call, put, take, select } from 'redux-saga/effects';
import {
  OPEN_MODAL,
  CLOSE_MODAL,
} from './constants';


function openModal(name) {
  /*eslint-disable */
  $(`#${name}`)
    .modal('setting', 'transition', 'Scale').modal('show');
  /*eslint-enable */
}

function closeModal(name) {
  /*eslint-disable */
  $(`#${name}`)
    .modal('setting', 'transition', 'Scale').modal('hide');
  /*eslint-enable */
}

function* modalWatcher() {
  while (true) {
    const action = yield take([OPEN_MODAL, CLOSE_MODAL]);
    if (action.type === OPEN_MODAL) {
      yield call(openModal, action.name);
    }
    if (action.type === CLOSE_MODAL) {
      yield call(closeModal, action.name);
    }
  }
}

export default modalWatcher;
