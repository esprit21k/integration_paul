import {
  WIDGET_CREATING,
  WIDGET_CREATE_SUCCESS,
  WIDGET_CREATE_ERROR,
} from './constants';

const initialState = {
  list: [], // where we'll store widgets
  requesting: false,
  successful: false,
  messages: [],
  errors: [],
};

const reducer = function widgetReducer(state = initialState, action) {
  switch (action.type) {
    case WIDGET_CREATING:
      return {
        ...state,
        requesting: true,
        successful: false,
        messages: [{
          body: `Widget: ${action.widget.name} being created...`,
          time: new Date(),
        }],
        errors: [],
      };

    // On success include the new widget into our list
    // We'll render this list later.
    case WIDGET_CREATE_SUCCESS:
      return {
        list: state.list.concat([action.widget]),
        requesting: false,
        successful: true,
        messages: [{
          body: `Widget: ${action.widget.name} awesomely created!`,
          time: new Date(),
        }],
        errors: [],
      };

    case WIDGET_CREATE_ERROR:
      return {
        ...state,
        requesting: false,
        successful: false,
        messages: [],
        errors: state.errors.concat([{
          body: action.error.toString(),
          time: new Date(),
        }]),
      };

    default:
      return state;
  }
};

export default reducer;
