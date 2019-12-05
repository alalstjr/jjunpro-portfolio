import React from "react"
import UniversityProvider from "../../university/UniversityProvider"
import { MainList, MainListContainer } from "../../../style/globalStyles"

const FirstSection = ({
  searchPlaces, 
  searchPlacesSetting, 
  categorySearch,
  initSearch,
  hendleInitSearch,
  hendleContain,
  mobile
}) => (
    <MainList 
      initSearch = {initSearch}
      mobile     = {mobile}
    >
      <MainListContainer 
        initSearch = {initSearch}
      >
        <UniversityProvider
          searchPlaces          = {searchPlaces}
          searchPlacesSetting   = {searchPlacesSetting}
          categorySearch        = {categorySearch}
          hendleInitSearch      = {hendleInitSearch}
          initSearch            = {initSearch}
          hendleContain         = {hendleContain}
          mobile                = {mobile}
        />
      </MainListContainer>
    </MainList>
);

export default FirstSection;