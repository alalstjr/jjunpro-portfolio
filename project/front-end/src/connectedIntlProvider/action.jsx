import { addLocaleData } from "react-intl";
import ko from "react-intl/locale-data/ko";
import en from "react-intl/locale-data/en";
import messages from "./messages";

export const DEFAULT_LOCALE = "en";

addLocaleData([...ko, ...en]);

export const ACTION_TYPES = { CHANGE_LOCALE: "LOCALE.CHANGE_LOCALE" };

export function getMessages(locale) {
  let localeData = messages[locale];
  if (!localeData) {
    localeData = messages[DEFAULT_LOCALE];
  }
  return localeData;
}

export function changeLocale(locale) {
  return {
    type: ACTION_TYPES.CHANGE_LOCALE,
    payload: {
      locale: locale,
    },
  };
}
