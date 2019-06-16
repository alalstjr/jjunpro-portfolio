import React from "react";

import { FooterAbout, FooterAboutUl, Copyright } from "../styleFooter";

const SecondSection = ({ intl }) => (
  <FooterAbout>
    <FooterAboutUl>
      <li>(주) 대한항공</li>
      <li>대표이사 : 쭌프로</li>
      <li>주소 : 서울특별시 강서구 하늘길 260</li>
      <li>대표전화 : 1588-2001 / 02-2656-2001</li>
    </FooterAboutUl>
    <FooterAboutUl>
      <li>사업자 등록번호 : (포트폴리오용 홈페이지)</li>
      <li>통신판매업신고 : 강서 제 16-3010</li>
      <li>개인정보보호책임자 : 여객사업본부장 이진호 상무 </li>
    </FooterAboutUl>
    <Copyright>
      © 1997-2019 KOREAN AIR
    </Copyright>
  </FooterAbout>
);

export default SecondSection;
