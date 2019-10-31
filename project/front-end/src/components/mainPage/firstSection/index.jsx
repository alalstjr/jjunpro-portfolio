import React from "react"
import UniversityProvider from "../../university/UniversityProvider"
import { MainList, MainListContainer } from "../../../style/globalStyles"

const FirstSection = ({
  searchPlaces, 
  searchPlacesSetting, 
  categorySearch,
  initSearch,
  hendleInitSearch
}) => (
    <MainList
      initSearch={initSearch}
    >
      <MainListContainer
        initSearch={initSearch}
      >
        <UniversityProvider
          searchPlaces={searchPlaces}
          searchPlacesSetting={searchPlacesSetting}
          categorySearch={categorySearch}
          hendleInitSearch={hendleInitSearch}
          initSearch={initSearch}
        />
      </MainListContainer>
    </MainList>
);

export default FirstSection;