import {
  REGISTER_REQUESTING,
  REGISTER_SUCCESS,
  REGISTER_ERROR,
} from './constants';

const initialState = {
  requesting: false,
  successful: false,
  messages: [],
  errors: [],
  fieldErrors: {},
};

const reducer = function registerReducer(state = initialState, action) {
  switch (action.type) {
    case REGISTER_REQUESTING:
      return {
        requesting: true,
        successful: false,
        messages: [{ body: 'Registering Trumpia Account..', time: new Date() }],
        errors: [],
        fieldErrors: {},
      };
    case REGISTER_SUCCESS:
      return {
        errors: [],
        messages: [{
          body: 'Successfully registered Trumpia account.',
          time: new Date(),
        }],
        requesting: false,
        successful: true,
        fieldErrors: {},
      };

    case REGISTER_ERROR:
      return {
        errors: state.errors.concat([{
          body: action.error,
          time: new Date(),
        }]),
        messages: [],
        requesting: false,
        successful: false,
        fieldErrors: {},
      };

    default:
      return state;
  }
};

export default reducer;
