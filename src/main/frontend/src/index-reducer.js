import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';
import client from './client/reducer';
import signup from './signup/reducer';
import login from './login/reducer';
import widgets from './widgets/reducer';
import topbar from './topbar/reducer';
import modal from './ui/modal/reducer';


const IndexReducer = combineReducers({
  signup,
  form,
  login,
  widgets,
  client,
  topbar,
  modal,
});

export default IndexReducer;
