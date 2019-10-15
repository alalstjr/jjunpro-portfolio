import React, { Component } from 'react'
import FirstSection from "./firstSection";
import SecondSection from "./secondSection";

import {Main} from "../../style/globalStyles";

class HomePage extends Component {

  constructor(props){
    super(props);
    this.state = {
      keyword: ""
    }
  }

  // Input Setup
  onChange = (e) => {
    this.setState({
        [e.target.name]: e.target.value
    });
  }

  render() {

    const { keyword } = this.state;

    return (
      <Main>
        <FirstSection
          keyword={keyword}
          onChange={this.onChange}
        />
        <SecondSection/>
      </Main>
    )
  }
}

export default HomePage;
