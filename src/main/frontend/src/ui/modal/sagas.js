import { call, put, take, select } from 'redux-saga/effects';
import {
  OPEN_MODAL,
  CLOSE_MODAL,
} from './constants';

function openModal(name) {
 $(`#${name}`)
    .modal('setting', 'transition', 'Scale').modal('show');
}

function closeModal(name) {
  $(`#${name}`)
    .modal('setting', 'transition', 'Scale').modal('hide');
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
