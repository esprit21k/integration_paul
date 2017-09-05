import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';
import client from './client/reducer';
import signup from './signup/reducer';
import login from './login/reducer';
import widgets from './widgets/reducer';
import topbar from './topbar/reducer';
import modal from './ui/modal/reducer';
import trumpiaRegisterModal from './trumpia/registerModal/reducer';


const IndexReducer = combineReducers({
  signup,
  form,
  login,
  widgets,
  client,
  topbar,
  trumpiaRegisterModal,
  modal,
});

export default IndexReducer;
