import React, { Component, Fragment, createRef } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { USER_AUTH } from "../../routes";

import FirstSection from "./firstSection";
import PugjjigProvider from "../../components/pugjjig/PugjjigProvider";

import KakaoMapService from "../../service/KakaoMapService";
import { getUniCountStoId } from "../../actions/PugjjigActions";
import { modalAccount } from "../../actions/accountActions";

import { Main, MainMap, MobileSearch } from "../../style/globalStyles";

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
      stoId: null,
      stoName: null,
      stoAddress: null,
      stoUrl: null,
      // Mobile Css
      mobile: false
    }

    this.appRef = createRef();
  }

  componentDidMount() {
    let map = new KakaoMapService(
      this,
      this.state.LatLng, 
      this.openModal,
      this.getUniCountStoId,
      this.hendleContain
    );
  
    this.setState({ 
      map,
      setLoading: false
    });
  }

  // Modal State
  openModal = (target, stoId, stoName, stoAddress, stoUrl) => {
    const { modalAccount } = this.props;

    // 로그인 사용자가 아닐경우
    if(target === "insertModalState" && !USER_AUTH()) {
      modalAccount("login", true); 
      return false;
    }

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
  getUniCountStoId = (storeId) => {
    return this.props.getUniCountStoId(storeId);
  }

  // 초기화면에서 검색하면 검색창 제거
  hendleInitSearch = () => {
    this.setState({
      initSearch: false
    });
  }

  hendleContain = (mobile) => {
    this.setState({
      mobile
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
      stoUrl,
      mobile
    } = this.state;
    
    return (
      <Main>
        {setLoading ?
          <div>불러오는 중입니다.</div>
          :
          <Fragment>
            <FirstSection
              searchPlaces          = {map.searchPlaces}
              searchPlacesSetting   = {map.searchPlacesSetting}
              categorySearch        = {map.categorySearch}
              initSearch            = {initSearch}
              hendleInitSearch      = {this.hendleInitSearch}
              hendleContain         = {this.hendleContain}
              mobile                = {mobile}
            />
            <MainMap
              ref = {this.appRef}
            />
            {
              (stoId !== null) ?
              <PugjjigProvider
                closeModal          = {this.closeModal}
                insertModalState    = {insertModalState}
                listModalState      = {listModalState}
                stoId               = {stoId}
                stoName             = {stoName}
                stoAddress          = {stoAddress}
                stoUrl              = {stoUrl}
              />
              :
              null
            }
          </Fragment>
        }
        {
          mobile ? 
          <MobileSearch>
            <button onClick={() => this.hendleContain(false)}>다시검색하기</button>
          </MobileSearch>
          :
          null
        }
      </Main>
    )
  }
}

MainProvider.propTypes = {
  getUniCountStoId: PropTypes.func.isRequired,
  modalAccount: PropTypes.func.isRequired,
  pugjjig_count: PropTypes.object.isRequired,
  modal_account: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired
}


const mapStateToProps = state => ({
  error: state.errors,
  pugjjig_count: state.pugjjig.pugjjig_count,
  modal_account: state.account.modal_account,
});

export default connect(
  mapStateToProps, 
  { getUniCountStoId, modalAccount }
)(MainProvider);