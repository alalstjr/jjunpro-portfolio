import $script from 'scriptjs'
import { Item, ItemUniName, ItemUniCount, Pagination } from '../components/university/style'
import markerIcon from '../static/images/kakao/marker.png';
import { async } from 'q';

const API_KEY = "e4886ec63d8dacf6d7f11ab426759a84";
const KAKAO_URL =  `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${API_KEY}&libraries=services`;

class KakaoMapService {

    /****************************************
        변수
    ****************************************/
    markers          = [];
    thatThis         = null;
    latLng           = null;
    openModal        = null;
    getUniCountStoId = null;
    hendleContain    = null;

    constructor(
        thatThis,
        latLng,
        openModal,
        getUniCountStoId,
        hendleContain
    ) {
        this.thatThis         = thatThis
        this.latLng           = latLng
        this.openModal        = openModal
        this.getUniCountStoId = getUniCountStoId
        this.hendleContain    = hendleContain

        // kakao 콜백함수에 전달해주는 변수
        const classThis = this;

        $script(KAKAO_URL, () => {
            /*global kakao*/ 
            kakao.maps.load(function () {
                // v3가 모두 로드된 후, 이 콜백 함수가 실행됩니다.
                thatThis.kakao = kakao;
                thatThis.map = new kakao.maps.Map(thatThis.appRef.current, {
                    center: new thatThis.kakao.maps.LatLng(latLng.y, latLng.x), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                });

                thatThis.infowindow = new kakao.maps.InfoWindow({
                    zIndex:1
                });

                thatThis.customOverlay = new kakao.maps.CustomOverlay({
                    xAnchor: 0.3,
                    yAnchor: 0.91
                });

                thatThis.defaultAddr = (x, y) => {
                    return new thatThis.kakao.maps.LatLng(y, x);
                };

                thatThis.latLng = (x, y) => { 
                    return new thatThis.kakao.maps.LatLng(x, y);
                }
                
                // classThis.categorySearch();
            });
        });
    }

    /****************************************
        키워드 검색을 요청하는 함수입니다.
    ****************************************/
    searchPlaces = () => {
        let places = new kakao.maps.services.Places();

        let keyword = document.getElementById('keyword').value;

        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            alert('키워드를 입력해주세요!');
            return false;
        }

        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        // places.keywordSearch( keyword, this.searchPlacesCB); 
        places.keywordSearch( keyword, this.searchPlacesCB, {
            category_group_code :"FD6",
            radius: 500
        }); 
        return false;
    } 

    /****************************************
        장소검색이 완료됐을 때 호출되는 콜백함수 입니다.
    ****************************************/
    searchPlacesCB = (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
            // console.log(data)

            // 정상적으로 검색이 완료됐으면
            // 커스텀 오버레이를 초기화 동시에 
            // 검색 목록과 (마커, 오버레이)를 표출합니다
            this.thatThis.customOverlay.setMap(null);
            this.displayPlaces(data);
            
            // 페이지 번호를 표출합니다
            this.displayPagination(pagination);

        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

            this.searchLoading('검색 결과가 존재하지 않습니다.');
            return;

        } else if (status === kakao.maps.services.Status.ERROR) {

            this.searchLoading('검색 결과 중 오류가 발생했습니다.');
            return;

        }
    }

    /****************************************
        검색 결과 목록과 마커를 표출하는 함수입니다.
    ****************************************/
    displayPlaces = async (places) => {

        // 좌측 리스트
        let listEl = document.getElementById('universityList');
        let fragment = document.createDocumentFragment();
        let bounds = new kakao.maps.LatLngBounds();
        
        // 검색 결과 목록에 추가된 항목들을 제거합니다
        this.removeAllChildNods(listEl);

        // 지도에 표시되고 있는 마커를 제거합니다
        this.removeMarker();
        
        for ( let i=0; i<places.length; i++ ) {

            // 마커를 생성하고 지도에 표시합니다
            let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
            let marker = this.addMarker(placePosition, i);
            let itemEl = await this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다
            this.setOverInfo(marker, places[i], placePosition, itemEl);

            fragment.appendChild(itemEl);
        }

        // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
        listEl.appendChild(fragment);

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        this.thatThis.map.setBounds(bounds);
    }

    /****************************************
        인포윈도우 커스텀 오버레이 생성
    ****************************************/
    setOverInfo = (marker, store, placePosition, itemEl) => {
        // kakao 콜백함수에 전달해주는 변수
        const classThis = this;
        const thatThis = this.thatThis;

        // 인포윈도우
        kakao.maps.event.addListener(marker, 'mouseover', function() {
            thatThis.infowindow.close();
            classThis.displayInfowindow( marker, store);
        });
        kakao.maps.event.addListener(marker, 'mouseout', function() {
            thatThis.infowindow.close();
        });

        // 커스텀 오버레이
        kakao.maps.event.addListener(marker, 'click', function() {
            thatThis.infowindow.close();
            classThis.displayOverlay(placePosition, store);
        });

        itemEl.onmouseover =  function () {
            classThis.displayInfowindow(marker, store);
        };
        itemEl.onclick =  function () {
            thatThis.infowindow.close();
            classThis.displayOverlay(placePosition, store);
        };

        itemEl.onmouseout =  function () {
            thatThis.infowindow.close();
        };
    }

    /****************************************
        검색결과 항목을 Element로 반환하는 함수입니다.
    ****************************************/
    getListItem = async (index, places) => {

        let pugjjig = new Object;
        pugjjig = await this.getUniCountStoId(places.id);

        let el = document.createElement('button');
        let item = `
            <span class="${ItemUniName.componentStyle.componentId} ${ItemUniName.componentStyle.lastClassName}">${places.place_name}</span>
            <span class="${ItemUniCount.componentStyle.componentId} ${ItemUniCount.componentStyle.lastClassName}">리뷰 ${pugjjig.count}개</span>
        `;       

        el.innerHTML = item;
        el.className = `${Item.componentStyle.componentId} ${Item.componentStyle.lastClassName}`;

        return el;
    }

    /****************************************
        마커를 생성하고 지도 위에 마커를 표시하는 함수입니다.
    ****************************************/
    addMarker = (position, idx, title) => {
        let imageSrc = markerIcon, // 마커 이미지 url, 스프라이트 이미지를 씁니다
            imageSize = new kakao.maps.Size(38, 38),  // 마커 이미지의 크기
            imgOptions =  {
                offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
                marker = new kakao.maps.Marker({
                position: position, // 마커의 위치
                image: markerImage 
            });

        marker.setMap(this.thatThis.map); // 지도 위에 마커를 표출합니다
        this.markers.push(marker);  // 배열에 생성된 마커를 추가합니다

        return marker;
    }


    /****************************************
        지도 위에 표시되고 있는 마커를 모두 제거합니다.
    ****************************************/
    removeMarker = () => {
        for ( let i = 0; i < this.markers.length; i++ ) {
            this.markers[i].setMap(null);
        }   
        this.markers = [];
    }

    /****************************************
        검색결과 목록 하단에 페이지번호를 표시는 함수입니다.
    ****************************************/
    displayPagination = (pagination) => {
        let paginationEl = document.getElementsByClassName(Pagination.componentStyle.componentId).item(0);
        let fragment = document.createDocumentFragment();
        let i; 

        // 기존에 추가된 페이지번호를 삭제합니다
        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild (paginationEl.lastChild);
        }

        for (i=1; i<=pagination.last; i++) {
            let el = document.createElement('button');
            el.type = "button";
            el.innerHTML = i;

            if (i===pagination.current) {
                el.id = 'on';
            } else {
                el.onclick = (function(i) {
                    return function() {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    }

    /****************************************
        검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다.
        인포윈도우에 장소명을 표시합니다.
    ****************************************/
    displayInfowindow = (marker, title) => {
        // kakao 콜백함수에 전달해주는 변수
        const thatThis = this.thatThis;

        let content = `
            <div style="padding:5px;z-index:1;text-align: center;font-size: 14px;font-weight: 400;">
                ${title.place_name}
            </div>
        `;
        
        thatThis.infowindow.setContent(content);
        thatThis.infowindow.open(thatThis.map, marker);
    }

    /****************************************
        커스텀 오버레이를 생성합니다
    ****************************************/
    displayOverlay = async (placePosition, store) => {
        // kakao 콜백함수에 전달해주는 변수
        const classThis = this;

        this.hendleContain(true);

        // 푹찍 리뷰 오브젝트 생성
        let pugjjig = new Object;
        pugjjig = await this.getUniCountStoId(store.id);

        let overlayWarp = document.createElement("div"); 
        overlayWarp.setAttribute("style", "padding:10px 15px;z-index:3;background-color: #fff;border: 1px solid #d11d33;width: 300px;");

        let overlayTitle = document.createElement("div"); 
        let overlayTitleText = document.createTextNode(store.place_name); 
        overlayTitle.appendChild(overlayTitleText);
        overlayTitle.setAttribute("style", "font-weight: 500;font-size: 16px;margin-bottom: 3px;");

        let overlayClose = document.createElement("button"); 
        let overlayCloseText = document.createTextNode(`X`); 
        overlayClose.appendChild(overlayCloseText);
        overlayClose.setAttribute("style", "color: #000;position: absolute;right: 16px;top: 6px;font-size: 16px;font-weight: 400;");
        overlayClose.addEventListener('click', function(){
            this.parentNode.setAttribute("style", "display: none");
        });

        let overlayReview = document.createElement("button"); 
        overlayReview.type = "button";
        let overlayReviewText = document.createTextNode(`평점 0점 | 리뷰 ${pugjjig.count}개 리뷰보기`); 
        overlayReview.appendChild(overlayReviewText);
        overlayReview.setAttribute("style", "color: #d11d33;font-weight: 400; margin-bottom: 4px;");

        overlayReview.addEventListener('click', function(){
            classThis.openModal("listModalState", store.id, store.place_name, store.address_name, store.place_url);
        });

        let overlayAddr = document.createElement("div"); 
        let overlayAddrText = document.createTextNode(`${store.address_name}`); 
        overlayAddr.appendChild(overlayAddrText);
        overlayAddr.setAttribute("style", "color: #000;");
        
        let overlayPhone = document.createElement("a");
        overlayPhone.href = `tel: ${store.phone}`;
        overlayPhone.target = `_blank`;
        let overlayPhoneText = document.createTextNode(`${store.phone}`); 
        overlayPhone.appendChild(overlayPhoneText);
        overlayPhone.setAttribute("style", "color: #288756;");

        let overlayInfo = document.createElement("a"); 
        overlayInfo.href = `${store.place_url}`;
        overlayInfo.target = `_blank`;
        let overlayInfoText = document.createTextNode(`상세정보`); 
        overlayInfo.appendChild(overlayInfoText);
        overlayInfo.setAttribute("style", "color: #3d75cc;margin-left: 5px;");

        let overlayWrite = document.createElement("button"); 
        let overlayWritewText = document.createTextNode("리뷰 작성하기"); 
        overlayWrite.appendChild(overlayWritewText);
        overlayWrite.setAttribute("style", "width: 100%;display: block;background-color: #d11d33;color: #fff;padding: 3px 0;border-radius: 3px;margin-top: 10px;");

        overlayWarp.appendChild(overlayTitle);
        overlayWarp.appendChild(overlayClose);
        overlayWarp.appendChild(overlayReview);
        overlayWarp.appendChild(overlayAddr);
        overlayWarp.appendChild(overlayPhone);
        overlayWarp.appendChild(overlayInfo);
        overlayWarp.appendChild(overlayWrite);
        
        overlayWrite.addEventListener('click', function(){
            classThis.storeSet(store.id, store.place_name, store.address_name, store.place_url);
        });

        this.thatThis.customOverlay.setPosition(placePosition);
        this.thatThis.customOverlay.setContent(overlayWarp);

        this.thatThis.customOverlay.setMap(this.thatThis.map);
    }

    /*
     * DB 에 저장하는 STORE 정보를 담아주도록 해주는 메소드
     * API 에서 받은 정보를 담아줍니다.
     */
    storeSet = (id, place_name, address_name, place_url) => {
        this.openModal("insertModalState", id, place_name, address_name, place_url);
    }

    /****************************************
        검색결과 목록의 자식 Element를 제거하는 함수입니다.
    ****************************************/
    removeAllChildNods = (el) => {
        while (el.hasChildNodes()) {
            el.removeChild (el.lastChild);
        }
    }

    /*-map review-*/

    /****************************************
        카테고리 검색
    ****************************************/
    categorySearch = (x, y) => {       
        this.searchLoading("음식점을 찾고있습니다.");

        // defaultAddr 변수로 대학교 위치를 입력받아 근처 맛집을 탐색
        let defaultAddr = this.thatThis.defaultAddr(x, y);

        this.thatThis.map.setCenter(defaultAddr);
        
        // 장소 검색 객체를 생성합니다
        var places = new kakao.maps.services.Places(this.thatThis.map); 
        // 카테고리로 은행을 검색합니다
        places.categorySearch('FD6', this.placesSearchCB, {
            useMapBounds: true, 
            useMapCenter: true,
            radius: 500
        });
    }

    // 키워드 검색 완료 시 호출되는 콜백함수 입니다
    placesSearchCB = async (places, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {

            // 키워드 검색시 표시된 customOverlay 제거
            this.thatThis.customOverlay.setMap(null);

            // 좌측 리스트
            let listEl = document.getElementById('universityList');
            let fragment = document.createDocumentFragment();
            let bounds = new kakao.maps.LatLngBounds();

            // 검색 결과 목록에 추가된 항목들을 제거합니다
            this.removeAllChildNods(listEl);

            // 지도에 표시되고 있는 마커를 제거합니다
            this.removeMarker();

            for (var i=0; i<places.length; i++) {
                
                // 마커를 생성하고 지도에 표시합니다
                let placePosition = this.thatThis.latLng(places[i].y, places[i].x);
                let marker = this.addMarker(placePosition, i);
                let itemEl = await this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                // LatLngBounds 객체에 좌표를 추가합니다
                bounds.extend(placePosition);
                this.setOverInfo(marker, places[i], placePosition, itemEl);

                fragment.appendChild(itemEl);
            }       

            // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
            listEl.appendChild(fragment);

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
            this.thatThis.map.setBounds(bounds);

            // 페이지 번호를 표출합니다
            this.displayPagination(pagination);
        }
    }

    /****************************************
        검색 호출동안 로딩 표시
    ****************************************/
   searchLoading = (text) => {
        // 검색 loading 표시
        let listEl = document.getElementById('universityList');
        this.removeAllChildNods(listEl);
        let loading = document.createElement("div");
        let loadingText = document.createTextNode(text); 
        loading.appendChild(loadingText);
        loading.setAttribute("style", "text-align: center;padding: 20px 0;background: #fff;z-index: 3;position: relative;");
        listEl.appendChild(loading);
   }

    /****************************************
        사용자 설정 검색을 요청하는 함수입니다.
    ****************************************/
    searchPlacesSetting = (x, y, radius, keyword) => {
        this.searchLoading("음식점을 찾고있습니다.");

        let cate;
        let places = new kakao.maps.services.Places();

        // defaultAddr 변수로 대학교 위치를 입력받아 근처 맛집을 탐색
        let defaultAddr = this.thatThis.defaultAddr(x, y);
        this.thatThis.map.setCenter(defaultAddr);

        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            alert('키워드를 입력해주세요!');
            return false;
        }

        // 사용자 설정이 카페인지 음식적인지 구분하는 조건문
        keyword.indexOf("카페") !== -1 ? cate = "CE7" : cate = "FD6";

        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        // defaultAddr 값은 필수입니다. 검색 거리에 기준점이 됩니다.
        places.keywordSearch( keyword, this.searchPlacesCB, {
            category_group_code : cate,
            location: defaultAddr,
            useMapBounds: true, 
            useMapCenter: true,
            radius: radius*1
        }); 

        return false;
    } 
}

export default KakaoMapService;