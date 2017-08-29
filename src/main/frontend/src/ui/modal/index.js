import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { openModal, closeModal } from './actions';

class Modal extends Component {
  static propTypes = {
    header: PropTypes.string,
    submitText: PropTypes.string,
    modalID: PropTypes.string,
    children: PropTypes.oneOfType([
      PropTypes.arrayOf(PropTypes.node),
      PropTypes.node
    ]),
    closeModal: PropTypes.func,
    openModal: PropTypes.func,
  }
  static defaultProps = {
    header: 'Info modal',
    submitText: 'Submit',
    modalID: 'asdf',
  }

  closeM = () => {
    this.props.closeModal({ name: this.props.modalID });
  }

  openM = () => {
    this.props.openModal({ name: this.props.modalID });
  }
  render() {
    return (
      <div id={this.props.modalID} className="ui modal">
        <i className="close icon" />
        <div className="header">{this.props.header}</div>
        <div className="content">
          {this.props.children}
        </div>
        <div className="actions">
          <div className="ui button" onClick={this.closeM}>Cancel</div>
          <div className="ui button">{this.props.submitText}</div>
        </div>
      </div>
    );
  }

}

const mapStateToProps = state => ({
});

const connected = connect(mapStateToProps, { openModal, closeModal })(Modal);


export default connected;
