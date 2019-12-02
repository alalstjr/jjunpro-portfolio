import React, { Component, Fragment, createRef } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"

import FirstSection from "./firstSection"
import PugjjigProvider from "../../components/pugjjig/PugjjigProvider"

import KakaoMapService from "../../service/KakaoMapService"
import { pugjjigGetCount } from "../../actions/KakaoMapActions"

import { Main, MainMap } from "../../style/globalStyles"

class MainProvider extends Component {

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
      // 초기 화면
      initSearch: true,
      // 상점 정보 리뷰작성시 필요정보 PugjjigProvider로 넘어갑니다.
      stoId: "",
      stoName: "",
      stoAddress: "",
      stoUrl: ""
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
  openModal = (target, stoId, stoName, stoAddress, stoUrl) => {
    this.setState({
      [target]: true,
      stoId,
      stoName,
      stoAddress,
      stoUrl
    });
  }
  closeModal = (target) => {
    this.setState({
      [target]: false,
      stoId: "",
      stoName: "",
      stoAddress: "",
      stoUrl: ""
    });
  }

  // 음식점 리뷰 갯수 가져오는 함수
  pugjjigGetCount = (storeId) => {
    return this.props.pugjjigGetCount(storeId);
  }

  // 초기화면에서 검색하면 검색창 제거
  hendleInitSearch = () => {
    this.setState({
      initSearch: false
    });
  }

  render() {
    // state Init
    const { 
      map, 
      setLoading, 
      insertModalState, 
      listModalState, 
      initSearch,
      stoId,
      stoName,
      stoAddress,
      stoUrl
    } = this.state;
    
    return (
      <Main>
        {setLoading ?
          <div>불러오는 중입니다.</div>
          :
          <Fragment>
            <FirstSection
              searchPlaces        = {map.searchPlaces}
              searchPlacesSetting = {map.searchPlacesSetting}
              categorySearch      = {map.categorySearch}
              initSearch          = {initSearch}
              hendleInitSearch    = {this.hendleInitSearch}
            />
            <MainMap
              ref = {this.appRef}
            />
            <PugjjigProvider
              closeModal          = {this.closeModal}
              insertModalState    = {insertModalState}
              listModalState      = {listModalState}
              stoId               = {stoId}
              stoName             = {stoName}
              stoAddress          = {stoAddress}
              stoUrl              = {stoUrl}
            />
          </Fragment>
        }
      </Main>
    )
  }
}

MainProvider.propTypes = {
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
  { pugjjigGetCount }
)(MainProvider);