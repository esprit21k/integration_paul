import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class Dashboard extends Component {
  static propTypes = {
    children: PropTypes.node,
  }


  render() {
    return (
      <div className="pusher">
        {this.props.children}
      </div>
    );
  }
}

// Grab only the piece of state we need
const mapStateToProps = state => ({
  login: state.login,
});

// make Redux state piece of `login` and our action `loginRequest`
// available in this.props within our component
const connected = connect(mapStateToProps, { })(Dashboard);


// Export our well formed login component
export default connected;
