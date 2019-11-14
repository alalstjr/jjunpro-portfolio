import React, { Component, createRef } from 'react'
import $script from 'scriptjs'
import routes from '../../../../routes'
import { MainMap } from '../../../../style/globalStyles'

class MapApi extends Component {

    constructor(props){
        super(props);
        
        this.state = {
            API_KEY: routes.apiKey,
            setLoading: true,
            LatLng: {
                x : 33.450701,
                y : 126.570667
            }
        }

        this.appRef = createRef();
    }

    componentDidMount() {
        this.setState({ 
            setLoading: false
        });
        this.setKakaoMap();
    }

    componentDidUpdate(){
        if (this.customOverlay){
            this.customOverlay.setMap(null);
            this.customOverlay = null;
        }
        this.setCenter();
        this.setOverLay();
    }

    // Map local default
    setCenter = ()=>{
        var LatLng = this.state.LatLng;
        let x = LatLng.x;
        let y = LatLng.y;

        if(typeof this.kakao === 'undefined' || this.kakao == null) return false;

        
        // 이동할 위도 경도 위치를 생성합니다 
        var moveLatLon = new this.kakao.maps.LatLng(y, x);
        // 지도 중심을 이동 시킵니다
        this.map.setCenter(moveLatLon);
    }

    // 오버레이 생성
    setOverLay = () => {
        var LatLng = this.state.LatLng;
        let x = LatLng.x;
        let y = LatLng.y;
        
        if (typeof this.kakao === 'undefined' || this.kakao == null) return false;

        console.log("도달")
        // 커스텀 오버레이에 표시할 내용입니다     
        // HTML 문자열 또는 Dom Element 입니다 
        var content = `
            <div id="overlay-area" class="arrow_box">
                <ul>
                    <li>도달</li>
                <ul>
            </div>
        `;

        // 커스텀 오버레이가 표시될 위치입니다 
        var position = new kakao.maps.LatLng(y, x);

        // 커스텀 오버레이를 생성합니다
        this.customOverlay = new kakao.maps.CustomOverlay({
            position: position,
            content: content
        });

        // 커스텀 오버레이를 지도에 표시합니다
        this.customOverlay.setMap(this.map);
    }

    // 카카오 맵 생성
    setKakaoMap = () => {
        var LatLng = this.state.LatLng;
        let x = LatLng.x;
        let y = LatLng.y;

        const {API_KEY} = this.state; 
        const kakao_url = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${API_KEY}`;
        
        var that = this;
        $script(kakao_url, () => {
            /*global kakao*/
            this.kakao = kakao;
            
            kakao.maps.load(function () {
                // v3가 모두 로드된 후, 이 콜백 함수가 실행됩니다.
                that.kakao = kakao;
                that.map = new kakao.maps.Map(that.appRef.current, {
                    center: new that.kakao.maps.LatLng(x, y), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                });
            });
        });
    }

    render() {
        const { setLoading } = this.state
        return (
            (setLoading) ?
                <div>불러오는 중입니다.</div>
                : 
                <MainMap ref={this.appRef}/>
        )
    }
}

export default MapApi;