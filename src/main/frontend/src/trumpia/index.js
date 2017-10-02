import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import TrumpiaRegisterModal from './registerModal';
import { openModal } from '../ui/modal/actions';
import { TRUMPIA_ACCOUNT_REGISTER_MODAL_ID } from './registerModal/constants';


class Trumpia extends Component {

  static propTypes = {
    openModal: PropTypes.func
  };

  newTrumpiaAccountModal = () => {
    this.props.openModal({ name: TRUMPIA_ACCOUNT_REGISTER_MODAL_ID });
  }

  render() {
    return (
      <div className="ui vertical stripe segment">
        <div className="ui middle aligned stackable grid container">
          <div className="row">
            <h2 className="ui center aligned icon header">
              <i className="circular users icon" />
              Trumpia Accounts
            </h2>
          </div>
          <div className="row">
            <div className="center aligned column">
              <div className="ui icon buttons">
                <button className="ui button" onClick={this.newTrumpiaAccountModal} >
                  <i className="plus icon" />
                </button>
                <button className="ui button">
                  <i className="Rocket icon" />
                </button>
              </div>
            </div>
          </div>
        </div>
        <TrumpiaRegisterModal
          modalID={Trumpia.modalID}
          header={'New Trumpia Account'}
          submitText={'Register account'}
        />
      </div>
    );
  }
}

// Grab only the piece of state we need
const mapStateToProps = state => ({
});

// make Redux state piece of `login` and our action `loginRequest`
// available in this.props within our component
const connected = connect(mapStateToProps, { openModal })(Trumpia);


export default connected;
