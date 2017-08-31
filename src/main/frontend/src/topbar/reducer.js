import {
  SIGNIN_REDIRECT,
  SIGNOUT,
} from './constants';

const reducer = function signinReducer(state = {}, action) {
  switch (action.type) {
    case SIGNIN_REDIRECT:
      return state;

    case SIGNOUT:
      return state;

    default:
      return state;
  }
};

export default reducer;
