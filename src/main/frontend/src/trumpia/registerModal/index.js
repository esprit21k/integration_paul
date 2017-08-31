import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Modal from '../../ui/modal';
import { TRUMPIA_ACCOUNT_REGISTER_MODAL_ID } from './constants';


class TrumpiaRegisterModal extends Component {

  static propTypes = {
  };

  render() {
    return (
      <Modal
        modalID={TRUMPIA_ACCOUNT_REGISTER_MODAL_ID}
        header={'New Trumpia Account'}
        submitText={'Register account'}
      >
        <form className="ui form">
          <div className="field">
            <label htmlFor="description">Please give a recognizable name to this account </label>
            <input type="text" name="description" placeholder="My main account" />
          </div>
          <div className="field">
            <label htmlFor="username">Please enter your Trumpia username</label>
            <input type="text" name="username" placeholder="foobar2017" />
          </div>
          <div className="field">
            <label htmlFor="apikey">Please enter your Trumpia API Key</label>
            <input type="text" name="apikey" placeholder="123456785901234561abcdef" />
          </div>
        </form>
      </Modal>
    );
  }
}

// Grab only the piece of state we need
const mapStateToProps = state => ({
});

// make Redux state piece of `login` and our action `loginRequest`
// available in this.props within our component
const connected = connect(mapStateToProps, null)(TrumpiaRegisterModal);


export default connected;
