import React, { Component, Fragment, createRef } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"

import FirstSection from "./firstSection"
import { MainMap } from "../../style/globalStyles"
import InsertModal from "../kakaoMap/InsertModal"
import ListModal from "../pugjjig/modal/ListModal"

import { Main } from "../../style/globalStyles"

import KakaoMapService from "../../service/KakaoMapService"
import { pugjjigInsert, pugjjigGetCount } from "../../actions/KakaoMapActions"

class HomePage extends Component {

  constructor(props){
    super(props);

    this.state = {
      // Map
      map: null,
      // Map state
      setLoading: true,
        LatLng: {
          x : 127.01639455013195,
          y : 37.64950880707469
      },
      // modal state
      insertModalState: false,
      listModalState: false,
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
      this.pugjjigGetCount
    );
  
    this.setState({ 
      map,
      setLoading: false
    });
  }

  // Modal State
  openModal = (target, id, address_name) => {
    this.setState({
      [target]: true,
      stoId: id,
      stoAddress: address_name
    });
  }
  closeModal = (target) => {
    this.setState({
      [target]: false,
      stoId: "",
      stoAddress: ""
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
    const { 
      map, 
      setLoading, 
      insertModalState, 
      listModalState, 
      stoId 
    } = this.state;
    
    return (
      <Main>
        {setLoading ?
          <div>불러오는 중입니다.</div>
          :
          <Fragment>
            <FirstSection
              searchPlaces={map.searchPlaces}
              searchPlacesSetting={map.searchPlacesSetting}
              categorySearch={map.categorySearch}
            />
            <MainMap
              ref = {this.appRef}
            />
            <InsertModal
              onChange = {this.onChange}
              onSubmit = {this.onSubmit}
              closeModal = {this.closeModal}
              modalState = {insertModalState}
            /> 
            <ListModal
              stoId = {stoId}
              modalState = {listModalState}
              closeModal = {this.closeModal}
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