import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Form, submit, reduxForm, Field } from 'redux-form';
import Modal from '../../ui/modal';
import { TRUMPIA_ACCOUNT_REGISTER_MODAL_ID } from './constants';
import registerRequest from './actions';
import CField from '../../ui/form/field';


function validate(values, props) {
  const errors = {};
  errors.description = props.formState.fieldErrors.description ?
                        props.formState.fieldErrors.description : null;
  if (!errors.description && (!values.description || values.description.trim() === '')) {
    errors.description = 'Enter a Description';
  }
  errors.username = props.formState.fieldErrors.username ?
                        props.formState.fieldErrors.username : null;
  if (!errors.username && (!values.username || values.username.trim() === '')) {
    errors.username = 'Enter a Username';
  }
  errors.apikey = props.formState.fieldErrors.apikey ?
                        props.formState.fieldErrors.apikey : null;
  if (!errors.apikey && (!values.apikey || values.apikey.trim() === '')) {
    errors.apikey = 'Enter a API Key';
  }
  return errors;
}


class TrumpiaRegisterModal extends Component {

  static propTypes = {
    handleSubmit: PropTypes.func,
    fields: PropTypes.array,
    register: PropTypes.shape({
      requesting: PropTypes.bool,
      successful: PropTypes.bool,
      messages: PropTypes.array,
      errors: PropTypes.array,
      fieldErrors: PropTypes.object,
    }),
    dispatch: PropTypes.func,
  };

  onSubmit = (values) => {
    console.log(this.props);
    console.log(values);
  }

  getForm = () => {
    this.props.dispatch(submit('register'));
    console.log("PROPZ");
    console.log(this.props);
  }

  render() {
    const {
      handleSubmit,
      fields,
      register: {
        requesting,
        successful,
        messages,
        errors,
        fieldErrors,
      },
    } = this.props;
    const required = value => value ? undefined : 'Required';
    return (
      <Modal
        modalID={TRUMPIA_ACCOUNT_REGISTER_MODAL_ID}
        header={'Connect Trumpia Account'}
        submitText={'Connect'}
        onSubmitClick={this.getForm}
        register={this.register}
      >
        <Form
          className="ui form register"
          onSubmit={handleSubmit(this.onSubmit)}
        >
          <Field
            name="description"
            type="text"
            component={CField}
            label="Please give a recognizable name to this account"
            required
            placeholder="My main Trumpia Account"
          />
          <Field
            name="username"
            type="text"
            component={CField}
            label="Please enter your Trumpia username"
            required
            placeholder="foobar2017"
          />
          <Field
            name="apikey"
            type="text"
            component={CField}
            label="Please enter your Trumpia API Key"
            required
            placeholder="123456785901234561abcdef"
          />
        </Form>
      </Modal>
    );
  }
}

const mapStateToProps = state => ({
  register: state.trumpiaRegisterModal,
});

const connected = connect(mapStateToProps, { registerRequest })(TrumpiaRegisterModal);
const formed = reduxForm({
  form: 'register',
  fields: ['description', 'username', 'apikey'],
  validate
})(connected);
const selformed = connect(
  (state) => {
    const thisState = state.trumpiaRegisterModal;
    return { formState: thisState };
  }
)(formed);


export default selformed;
