import React, { Component, Fragment, createRef } from 'react'
import PropTypes from "prop-types";
import { connect } from "react-redux";

import FirstSection from "./firstSection";
import { MainMap } from "../../style/globalStyles"
import InsertModal from "../kakaoMap/InsertModal"

import { Main } from "../../style/globalStyles";

import KakaoMapService from "../../service/KakaoMapService"
import { pugjjigInsert, pugjjigGetCount } from "../../actions/KakaoMapActions"

class HomePage extends Component {

  constructor(props){
    super(props);

    this.state = {
      // Map
      map: null,
      // 검색 키워드
      keyword: "",
      // Map state
      setLoading: true,
        LatLng: {
          x : 33.450701,
          y : 126.570667
      },
      // modal state
      modalState: false,
      // input value
      uniSubject: "",
      uniContent: "",
      uniName: "",
      uniTag: ["arr1","arr2"],
      uniStar: 1,
      // store value
      stoId: "",
      stoAddress: ""
    }

    this.appRef = createRef();
  }

  componentDidMount() {
    let map = new KakaoMapService(
      this,
      this.state.LatLng, 
      this.openModal, 
      this.storeSetUp,
      this.pugjjigGetCount
    );
  
    this.setState({ 
        map,
        setLoading: false
    });

    console.log(this.state.map)
  }

  // Modal State
  openModal = () => {
      this.setState({
          modalState: true
      });
  }
  closeModal = () => {
      this.setState({
          modalState: false
      });
  }

  // Store SetUp
  storeSetUp = (id, address_name) => {
      this.setState({
          stoId: id,
          stoAddress: address_name
      });
  }

  // Input Setup
  onChange = (e) => {
    this.setState({
        [e.target.name]: e.target.value
    });
  }

  // Form Submit
  onSubmit = (e) => {
      e.preventDefault();
      const { 
          uniSubject, 
          uniContent,
          uniName,
          uniTag,
          stoId,
          stoAddress
      } = this.state;

      const pugjjig = {
          uniSubject, 
          uniContent,
          uniName,
          uniTag,
          stoId,
          stoAddress
      };
      this.props.pugjjigInsert(pugjjig);
  }

  // 음식점 리뷰 갯수 가져오는 함수
  pugjjigGetCount = (storeId) => {
    return this.props.pugjjigGetCount(storeId);
  }

  render() {
    // state Init
    const { map, keyword, setLoading, modalState } = this.state;
  
    return (
      <Main>
        {setLoading ?
          <div>불러오는 중입니다.</div>
          :
          <Fragment>
            <FirstSection
              searchPlaces={map.searchPlaces}
              keyword={keyword}
              onChange={this.onChange}
            />
            <MainMap
              ref = {this.appRef}
            />
            <InsertModal
              modalState = {modalState}
              closeModal = {this.closeModal}
              onSubmit = {this.onSubmit}
              onChange = {this.onChange}
            /> 
          </Fragment>
        }
      </Main>
    )
  }
}

HomePage.propTypes = {
  pugjjigInsert: PropTypes.func.isRequired,
  pugjjigGetCount: PropTypes.func.isRequired,
  pugjjig_count: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired
}


const mapStateToProps = state => ({
  error: state.errors,
  pugjjig_count: state.pugjjig.pugjjig_count
});

export default connect(
  mapStateToProps, 
  { 
      pugjjigInsert, 
      pugjjigGetCount 
  }
)(HomePage);