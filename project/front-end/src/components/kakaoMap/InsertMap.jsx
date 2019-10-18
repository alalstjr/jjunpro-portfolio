import React, { Component, Fragment, createRef } from "react"
import PropTypes from "prop-types";
import { connect } from "react-redux";

import { API_KEY } from "../../routes"
import { MainMap } from "../../style/globalStyles"
import { setKakaoMap } from "../../service/KakaoMapService"
import InsertModal from "../kakaoMap/InsertModal"
import { pugjjigInsert, pugjjigGetCount } from "../../actions/KakaoMapActions"

class MapApi extends Component {
    constructor(props){
        super(props);
        
        this.state = {
            API_KEY: API_KEY,
            setLoading: true,
            LatLng: {
                x : 33.450701,
                y : 126.570667
            },
            // modal
            modalState: false,
            // input
            uniSubject: "",
            uniContent: "",
            uniName: "",
            uniTag: ["arr1","arr2"],
            uniStar: 1,
            // store
            stoId: "",
            stoAddress: ""
        }

        this.appRef = createRef();
    }

    componentDidMount() {
        this.setState({ 
            setLoading: false
        }); 
        setKakaoMap(
            this, 
            this.state.LatLng, 
            this.state.API_KEY, 
            this.openModal, 
            this.storeSetUp,
            this.pugjjigGetCount
        );
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

    // Input SetUp
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
        const { setLoading, modalState } = this.state

        return (
            (setLoading) ?
                <div>불러오는 중입니다.</div>
                :
                <Fragment>
                    <MainMap ref = {this.appRef}/>
                    <InsertModal
                        modalState = {modalState}
                        closeModal = {this.closeModal}
                        onSubmit = {this.onSubmit}
                        onChange = {this.onChange}
                    /> 
                </Fragment>
        )
    }
}

MapApi.propTypes = {
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
)(MapApi);