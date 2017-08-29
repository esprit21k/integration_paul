import {
  OPEN_MODAL,
  CLOSE_MODAL,
  ADD_MODAL_REF
} from './constants';

const initialState = {
  requesting: false,
  succesful: false,
  modalRef: [],
  errors: [],
};

const reducer = function modalReducer(state = initialState, action) {
  switch (action.type) {
    default:
      return state;
  }
};

export default reducer;
