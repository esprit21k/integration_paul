import React from 'react';

const renderField = ({ input, required, placeholder, name, label, type, meta: { touched, error, warning } }) => (
  <div className={(required ? 'required field' : 'field') + (touched && (error && ' error'))}>
    <label htmlFor={name}>{label}</label>
    <div>
      <input {...input} placeholder={placeholder} type={type} />
      {touched && ((error && <div className="ui pointing red basic label">{error}</div>) || (warning && <div className="ui pointing orange basic label">{warning}</div>))}
    </div>
  </div>
);
export default renderField;
