import React, { Component, Fragment, createRef } from 'react'
import routes from '../../routes'
import { MainMap } from '../../style/globalStyles'
import { setKakaoMap } from '../../service/KakaoMapService'
import InsertModal from '../kakaoMap/InsertModal'

class MapApi extends Component {

    constructor(props){
        super(props);
        
        this.state = {
            API_KEY: routes.apiKey,
            setLoading: true,
            LatLng: {
                x : 33.450701,
                y : 126.570667
            },
            // modal
            modalState: false
        }

        this.appRef = createRef();
    }

    componentDidMount() {
        this.setState({ 
            setLoading: false
        }); 
        setKakaoMap(this, this.state.LatLng, this.state.API_KEY, this.openModal);
    }

    openModal = () => {
        console.log("도달");
        // 모달창을 열고 닫을때 설정 초기화
        this.setState({
            modalState: true
        });
    }

    render() {
        const { setLoading, modalState } = this.state

        let modalContainer;

        const modalComponent = () => {
            return(
                <Fragment>
                    <MainMap ref={this.appRef}/>
                    {modalState ? <InsertModal/> : null} 
                </Fragment>
            );
        }
        modalContainer = modalComponent();

        return (
            (setLoading) ?
                <div>불러오는 중입니다.</div>
                :
                <Fragment>
                    {modalContainer}
                </Fragment>
        )
    }
}

export default MapApi;