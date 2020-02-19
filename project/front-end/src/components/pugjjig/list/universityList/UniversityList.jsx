import React, {Component, Fragment} from 'react';

import {
    FormGroup
} from "../../../../style/globalStyles";

import {InputClean, ModalCloseBtn} from "../../../../style/globalStyles";
import {UniBtn, UniList} from "../../style";
import {Item, ItemUniName} from "../../../university/style";

class UniversityList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            keyword: "",
            uniSearchState: false,
            // 대학교 목록
            university: [
                {
                    uniName: "주변대학없음"
                },
                {
                    uniName: "덕성여자대학교"
                },
                {
                    uniName: "덕성여자대학교 후문"
                },
                {
                    uniName: "서일대학교"
                },
                {
                    uniName: "건국대학교"
                },
                {
                    uniName: "홍익대학교 서울캠퍼스(홍대)"
                },
                {
                    uniName: "홍익대학교 세종캠퍼스(홍대)"
                },
                {
                    uniName: "명지대학교"
                },
                {
                    uniName: "명지전문대학"
                },
                {
                    uniName: "서울대학교"
                },
                {
                    uniName: "이화여자대학교 정문"
                },
                {
                    uniName: "이화여자대학교 후문"
                },
                {
                    uniName: "서울과학기술대학교"
                },
                {
                    uniName: "서울과학기술대학교 정문"
                },
                {
                    uniName: "서울교육대학교"
                },
                {
                    uniName: "육군사관학교"
                },
                {
                    uniName: "육군사관학교 육사아파트"
                },
                {
                    uniName: "육군사관학교 제2정문"
                },
                {
                    uniName: "한국과학기술원 KAIST"
                },
                {
                    uniName: "서울한국방송통신대학교"
                },
                {
                    uniName: "한국예술종합학교 석관동캠퍼스"
                },
                {
                    uniName: "한국예술종합학교 서초동캠퍼스"
                },
                {
                    uniName: "한국예술종합학교 대학로캠퍼스"
                },
                {
                    uniName: "한국체육대학교",
                },
                {
                    uniName: "서울시립대학교",
                },
                {
                    uniName: "가톨릭대학교 성신교정",
                },
                {
                    uniName: "감리교신학대학교",
                },
                {
                    uniName: "건국대학교 서울캠퍼스(건대)",
                },
                {
                    uniName: "경기대학교 서울캠퍼스",
                },
                {
                    uniName: "경희대학교 서울캠퍼스",
                },
                {
                    uniName: "고려대학교 서울캠퍼스",
                },
                {
                    uniName: "광운대학교",
                },
                {
                    uniName: "국민대학교",
                },
                {
                    uniName: "케이씨대학교",
                },
                {
                    uniName: "동국대학교 서울캠퍼스",
                },
                {
                    uniName: "동덕여자대학교",
                },
                {
                    uniName: "명지대학교 인문캠퍼스",
                },
                {
                    uniName: "삼육대학교",
                },
                {
                    uniName: "상명대학교 서울캠퍼스",
                },
                {
                    uniName: "서강대학교",
                },
                {
                    uniName: "서경대학교",
                },
                {
                    uniName: "서울기독대학교",
                },
                {
                    uniName: "서울여자대학교 서울캠퍼스",
                },
                {
                    uniName: "성공회대학교",
                },
                {
                    uniName: "성균관대학교 인문사회과학캠퍼스",
                },
                {
                    uniName: "성신여자대학교 돈암수정캠퍼스",
                },
                {
                    uniName: "세종대학교",
                },
                {
                    uniName: "숙명여자대학교",
                },
                {
                    uniName: "숭실대학교",
                },
                {
                    uniName: "연세대학교 서울캠퍼스",
                },
                {
                    uniName: "장로회신학대학교",
                },
                {
                    uniName: "중앙대학교 서울캠퍼스",
                },
                {
                    uniName: "총신대학교 사당캠퍼스",
                },
                {
                    uniName: "추계예술대학교",
                },
                {
                    uniName: "한국성서대학교",
                },
                {
                    uniName: "한국외국어대학교 서울캠퍼스",
                },
                {
                    uniName: "한성대학교",
                },
                {
                    uniName: "한양대학교 서울캠퍼스",
                },
                {
                    uniName: "서울한영대학교",
                },
                {
                    uniName: "명지전문대학(정)",
                },
                {
                    uniName: "동양미래대학교",
                },
                {
                    uniName: "인덕대학교",
                }
            ]
        }
    }

    onState = () => {
        const {uniSearchState} = this.state;
        const {handleUniName} = this.props;

        handleUniName("");
        this.setState({
            uniSearchState: !uniSearchState
        });
    }

    // Input Setup
    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    handleUniSet = (target) => {
        const {handleUniName} = this.props;

        handleUniName(target);
        this.setState({
            uniSearchState: false
        });
    }

    render() {
        // state Init
        const {university, uniSearchState, keyword} = this.state;
        const {uniName} = this.props;

        // Variables Init
        let universityContent;
        let universityList = [];

        // SearchFilte Init
        const filteredList = university.filter(
            info => info.uniName.indexOf(keyword) !== -1
        );

        // universityList
        const universityGet = (university) => {
            const data = university.map((university, index) => {
                return (
                    <Item key={index} onClick={() => this.handleUniSet(university.uniName)}>
                        <ItemUniName>{university.uniName}</ItemUniName>
                    </Item>
                );
            });

            for (let i = 0; i < data.length; i++) {
                universityList.push(data[i]);
            }

            return (
                <Fragment>
                    {universityList}
                </Fragment>
            );
        }

        // universityList Get List View
        universityContent = universityGet(filteredList);

        return (
            <FormGroup>
                <UniBtn>
                    {
                        uniName === "" ?
                            uniSearchState ?
                                <span>
                                <InputClean
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    value={keyword}
                                    onChange={this.onChange}
                                />
                                <ModalCloseBtn onClick={this.onState}/>
                            </span>
                                :
                                <span onClick={this.onState}>맛집 근처 대학교를 선택해 주세요.</span>
                            :
                            <span onClick={this.onState}>{uniName}</span>
                    }
                </UniBtn>
                {
                    uniSearchState ?
                        <UniList>
                            {universityContent}
                        </UniList>
                        :
                        null
                }
            </FormGroup>
        )
    }
}

export default UniversityList;