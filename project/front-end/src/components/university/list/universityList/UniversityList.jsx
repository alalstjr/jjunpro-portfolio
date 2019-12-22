import React, { Component, Fragment } from "react";
import PropTypes from "prop-types"
import { connect } from "react-redux"

import UniversityItem from "../item/UniversityItem";
import { getUniCountUniId } from "../../../../actions/PugjjigActions";

class UniversityList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            // 대학교 목록
            university: [
                {
                    id: 0,
                    uniName:"덕성여자대학교",
                    uniPujjig: 0,
                    x: 127.01639455013195,
                    y: 37.64950880707469
                },
                {
                    id: 1,
                    uniName:"덕성여자대학교 후문",
                    uniPujjig: 0,
                    x: 127.017288184806,
                    y: 37.6525503981283
                },
                {
                    id: 2,
                    uniName:"서일대학교",
                    uniPujjig: 0,
                    x: 127.097976337279,
                    y: 37.5864866143728
                },
                {
                    id: 3,
                    uniName:"건국대학교",
                    uniPujjig: 0,
                    x: 127.076302318843,
                    y: 37.5429556751421
                },
                {
                    id: 4,
                    uniName:"홍익대학교 서울캠퍼스(홍대)",
                    uniPujjig: 0,
                    x: 126.925554591431,
                    y: 37.550874837441
                },
                {
                    id: 5,
                    uniName:"홍익대학교 세종캠퍼스(홍대)",
                    uniPujjig: 0,
                    x: 127.287933647532,
                    y: 36.6213894816246
                },
                {
                    id: 6,
                    uniName:"명지대학교",
                    uniPujjig: 0,
                    x: 126.923891021078,
                    y: 37.5798245779138
                },
                {
                    id: 7,
                    uniName:"명지전문대학",
                    uniPujjig: 0,
                    x: 126.925693019531,
                    y: 37.5846910900534
                },
                {
                    id: 8,
                    uniName:"서울대학교",
                    uniPujjig: 0,
                    x: 126.948400031913,
                    y: 37.4663007082505
                },
                {
                    id: 9,
                    uniName:"이화여자대학교 정문",
                    uniPujjig: 0,
                    x: 126.94566937595,
                    y: 37.5594236592153
                },
                {
                    id: 10,
                    uniName:"이화여자대학교 후문",
                    uniPujjig: 0,
                    x: 126.944692996803,
                    y: 37.5635317504439
                },
                {
                    id: 11,
                    uniName:"서울과학기술대학교",
                    uniPujjig: 0,
                    x: 127.077526020922,
                    y: 37.6318648220506
                },
                {
                    id: 12,
                    uniName:"서울과학기술대학교 정문",
                    uniPujjig: 0,
                    x: 127.076713189525,
                    y: 37.6302291629891
                },
                {
                    id: 13,
                    uniName:"서울교육대학교",
                    uniPujjig: 0,
                    x: 127.01545948255408,
                    y: 37.48966512862403
                },
                {
                    id: 14,
                    uniName:"육군사관학교",
                    uniPujjig: 0,
                    x: 127.09894074445441,
                    y: 37.62393454516784
                },
                {
                    id: 15,
                    uniName:"육군사관학교 육사아파트",
                    uniPujjig: 0,
                    x: 127.09070361095021,
                    y: 37.62051380935745
                },
                {
                    id: 16,
                    uniName:"육군사관학교 제2정문",
                    uniPujjig: 0,
                    x: 127.095789403459,
                    y: 37.6257210991292
                },
                {
                    id: 17,
                    uniName:"한국과학기술원 KAIST",
                    uniPujjig: 0,
                    x: 127.04669366791835,
                    y: 37.59267722437258
                },
                {
                    id: 18,
                    uniName:"서울한국방송통신대학교",
                    uniPujjig: 0,
                    x: 127.045852880825,
                    y: 37.5473466763422
                },
                {
                    id: 19,
                    uniName:"한국예술종합학교 석관동캠퍼스",
                    uniPujjig: 0,
                    x: 127.057395168611,
                    y: 37.6061549320677
                },
                {
                    id: 20,
                    uniName:"한국예술종합학교 서초동캠퍼스",
                    uniPujjig: 0,
                    x: 127.010844385988,
                    y: 37.4780714638708
                },
                {
                    id: 21,
                    uniName:"한국예술종합학교 대학로캠퍼스",
                    uniPujjig: 0,
                    x: 126.996730370375,
                    y: 37.5825160092281
                },
                {
                    id: 22,
                    uniName:"한국체육대학교",
                    uniPujjig: 0,
                    x: 127.130811990731,
                    y: 37.5201630106531
                },
                {
                    id: 23,
                    uniName:"서울시립대학교",
                    uniPujjig: 0,
                    x: 127.058813732892,
                    y: 37.5839898437514
                },
                {
                    id: 24,
                    uniName:"가톨릭대학교 성신교정",
                    uniPujjig: 0,
                    x: 127.0045786211564,
                    y: 37.586336176926416
                },
                {
                    id: 25,
                    uniName:"감리교신학대학교",
                    uniPujjig: 0,
                    x: 126.962125913828,
                    y: 37.567856202812
                },
                {
                    id: 26,
                    uniName:"건국대학교 서울캠퍼스(건대)",
                    uniPujjig: 0,
                    x: 127.076302318843,
                    y: 37.5429556751421
                },
                {
                    id: 27,
                    uniName:"경기대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 126.962122732909,
                    y: 37.5651964618968
                },
                {
                    id: 28,
                    uniName:"경희대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.05285401582,
                    y: 37.5968011678013
                },
                {
                    id: 29,
                    uniName:"고려대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.031685000726,
                    y: 37.5898422803883
                },
                {
                    id: 30,
                    uniName:"광운대학교",
                    uniPujjig: 0,
                    x: 127.058270608867,
                    y: 37.6192404638865
                },
                {
                    id: 31,
                    uniName:"국민대학교",
                    uniPujjig: 0,
                    x: 126.996969239236,
                    y: 37.6107638961532
                },
                {
                    id: 32,
                    uniName:"케이씨대학교",
                    uniPujjig: 0,
                    x: 126.854164438887,
                    y: 37.548689099232
                },
                {
                    id: 33,
                    uniName:"동국대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.000398385089,
                    y: 37.5582396348315
                },
                {
                    id: 34,
                    uniName:"동덕여자대학교",
                    uniPujjig: 0,
                    x: 127.041812418483,
                    y: 37.6069903921957
                },
                {
                    id: 35,
                    uniName:"명지대학교 인문캠퍼스",
                    uniPujjig: 0,
                    x: 126.92282642165183,
                    y: 37.580234739838886
                },
                {
                    id: 36,
                    uniName:"삼육대학교",
                    uniPujjig: 0,
                    x: 127.10634537610252,
                    y: 37.64360562221563
                },
                {
                    id: 37,
                    uniName:"상명대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 126.955350026719,
                    y: 37.6023222243288
                },
                {
                    id: 38,
                    uniName:"서강대학교",
                    uniPujjig: 0,
                    x: 126.93927007202764,
                    y: 37.55123227549557
                },
                {
                    id: 39,
                    uniName:"서경대학교",
                    uniPujjig: 0,
                    x: 127.01332889505409,
                    y: 37.615224879059106
                },
                {
                    id: 40,
                    uniName:"서울기독대학교",
                    uniPujjig: 0,
                    x: 126.91256378597808,
                    y: 37.60062243984443
                },
                {
                    id: 41,
                    uniName:"서울여자대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.091256293837,
                    y: 37.6278582278924
                },
                {
                    id: 42,
                    uniName:"성공회대학교",
                    uniPujjig: 0,
                    x: 126.82558113494811,
                    y: 37.48770654488821
                },
                {
                    id: 43,
                    uniName:"성균관대학교 인문사회과학캠퍼스",
                    uniPujjig: 0,
                    x: 126.99368669186882,
                    y: 37.58817051818796
                },
                {
                    id: 44,
                    uniName:"성신여자대학교 돈암수정캠퍼스",
                    uniPujjig: 0,
                    x: 127.022129316251,
                    y: 37.5916680669509
                },
                {
                    id: 45,
                    uniName:"세종대학교",
                    uniPujjig: 0,
                    x: 127.0735094176659,
                    y: 37.551920581618205
                },
                {
                    id: 46,
                    uniName:"숙명여자대학교",
                    uniPujjig: 0,
                    x: 126.9647258470644,
                    y: 37.54643849947151
                },
                {
                    id: 47,
                    uniName:"숭실대학교",
                    uniPujjig: 0,
                    x: 126.9575312336077,
                    y: 37.496398040356574
                },
                {
                    id: 48,
                    uniName:"연세대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 126.938389155713,
                    y: 37.5655576723744
                },
                {
                    id: 49,
                    uniName:"장로회신학대학교",
                    uniPujjig: 0,
                    x: 127.104085276667,
                    y: 37.5503875150275
                },
                {
                    id: 50,
                    uniName:"중앙대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 126.95587230092029,
                    y: 37.50406409257192
                },
                {
                    id: 51,
                    uniName:"총신대학교 사당캠퍼스",
                    uniPujjig: 0,
                    x: 126.967357782152,
                    y: 37.4896760468197
                },
                {
                    id: 52,
                    uniName:"추계예술대학교",
                    uniPujjig: 0,
                    x: 126.953431589411,
                    y: 37.5624615329189
                },
                {
                    id: 53,
                    uniName:"한국성서대학교",
                    uniPujjig: 0,
                    x: 127.06434940922883,
                    y: 37.64899505334899
                },
                {
                    id: 54,
                    uniName:"한국외국어대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.058946829707,
                    y: 37.5977136891025
                },
                {
                    id: 55,
                    uniName:"한성대학교",
                    uniPujjig: 0,
                    x: 127.010225523923,
                    y: 37.5825624632779
                },
                {
                    id: 56,
                    uniName:"한양대학교 서울캠퍼스",
                    uniPujjig: 0,
                    x: 127.04532959264,
                    y: 37.5577588114444
                },
                {
                    id: 57,
                    uniName:"서울한영대학교",
                    uniPujjig: 0,
                    x: 126.851529283597,
                    y: 37.4965322224543
                },
                {
                    id: 58,
                    uniName:"명지전문대학(정)",
                    uniPujjig: 0,
                    x: 126.925693019531,
                    y: 37.5846910900534
                },
                {
                    id: 59,
                    uniName:"동양미래대학교",
                    uniPujjig: 0,
                    x: 126.867675063634,
                    y: 37.5006023324227
                },
                {
                    id: 60,
                    uniName:"인덕대학교",
                    uniPujjig: 0,
                    x: 127.05493767196094,
                    y: 37.63082785304347
                }
            ]
        }
    }

    componentDidMount() {
        const { university } = this.state;
        const { getUniCountUniId } = this.props;

        university.map(uni => {
            getUniCountUniId(uni.uniName);
        });
    }

    componentWillReceiveProps(nextProps) {
        const { university } = this.state;
        const { uni_count } = this.props;

        if(nextProps.uni_count !== uni_count) {
            if(uni_count.data !== undefined) {
                let preData = university.filter(
                    info => info.uniName === uni_count.data.uniName
                );

                // 서버에서 받은 해당 대학교의 리뷰 갯수 업데이트
                preData.uniPujjig = uni_count.data.count*1;

                this.setState({
                    university: university.map(
                    uni =>  (uni.uniName === uni_count.data.uniName)
                        ? { ...uni, ...preData } // 새 객체를 만들어서 기존의 값과 전달받은 data 을 덮어씀
                        : uni // 기존의 값을 그대로 유지
                    )
                });           
            }
        }
    }

    render() {
        // state Init
        const { university } = this.state;

        // props Init
        const { keyword, storeState, onSearch, onSearchSetting } = this.props;

        // Variables Init
        let universityContent;
        let universityList = [];

        // SearchFilte Init
        const filteredList = university.filter(
            info => info.uniName.indexOf(keyword) !== -1
        );

        // universityList
        const universityGet = (university) => {
            const data = university.map(university => {
                return (
                    <UniversityItem
                        key = {university.id}
                        university = {university}
                        uniName = {university.uniName}
                        uniPujjig = {university.uniPujjig}
                        categorySearch = {
                            storeState === 1 ?
                            () => onSearch(university.x, university.y, university.uniName)
                            :
                            storeState === 3 ?
                            () => onSearchSetting(university.x, university.y, university.uniName)
                            :
                            null
                        }
                    />
                );
            });

            for(let i = 0; i < data.length; i++) {
                universityList.push(data[i]);
            }

            return (
                <Fragment>
                    {universityList}
                </Fragment>
            );
        }

        // universityList Get List View
        universityContent = (keyword !== "") ? universityGet(filteredList) : null;

        return (
            <div>
                {universityContent}
            </div>
        )
    }
}

UniversityList.propTypes = {
    getUniCountUniId: PropTypes.func.isRequired,
    uni_count: PropTypes.object.isRequired,
    error: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    error: state.errors,
    uni_count: state.pugjjig.uni_count
});
  
export default connect(
    mapStateToProps, 
    { getUniCountUniId }
)(UniversityList);