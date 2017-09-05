import React from 'react';
import PropTypes from 'prop-types';
// Iterate over each error object and print them
// in an unordered list
const Errors = (props) => {
  const { errors } = props;
  return (
    <div>
      <ul>
        {errors.map(errors2 => (
          <li key={errors2.time}>{errors2.body}</li>
        ))}
      </ul>
    </div>
  );
};

Errors.propTypes = {
  errors: PropTypes.arrayOf(
      PropTypes.shape({
        body: PropTypes.string,
        time: PropTypes.date,
      })),
};

export default Errors;
