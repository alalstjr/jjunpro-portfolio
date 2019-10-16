import React, { Component, Fragment, createRef } from "react"
import PropTypes from "prop-types";
import { connect } from "react-redux";

import { API_KEY } from "../../routes"
import { MainMap } from "../../style/globalStyles"
import { setKakaoMap } from "../../service/KakaoMapService"
import InsertModal from "../kakaoMap/InsertModal"
import { pugjjigInsert } from "../../actions/KakaoMapActions"

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
            // important
            placePosition: "",
            // input
            uniSubject: "",
            uniContent: "",
            uniName: "",
            uniTag: ["arr1","arr2"],
            uniStar: 1,
        }

        this.appRef = createRef();
    }

    componentDidMount() {
        this.setState({ 
            setLoading: false
        }); 
        setKakaoMap(this, this.state.LatLng, this.state.API_KEY, this.openModal, this.importantSetUp);
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

    // Important SetUp
    importantSetUp = (important) => {
        this.setState({
            placePosition: JSON.stringify(important)
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
            placePosition, 
            uniSubject, 
            uniContent,
            uniName,
            uniTag
         } = this.state;

        const pugjjig = {
            placePosition,
            uniSubject, 
            uniContent,
            uniName,
            uniTag
        };
        this.props.pugjjigInsert(pugjjig);
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

pugjjigInsert.propTypes = {
    pugjjigInsert: PropTypes.func.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors
});

export default connect(
    mapStateToProps, 
    { pugjjigInsert }
)(MapApi);