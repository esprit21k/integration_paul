import {
  OPEN_MODAL,
  CLOSE_MODAL
} from './constants';

export const openModal = function openModal({ name }) {
  return {
    type: OPEN_MODAL,
    name
  };
};

export const closeModal = function closeModal({ name }) {
  return {
    type: CLOSE_MODAL,
    name
  };
};
