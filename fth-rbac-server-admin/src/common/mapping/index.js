import ResourceMapping from './modules/resource';
import ApplicationMapping from './modules/application';
import UserMapping from './modules/user';

const MappingTools = {
  // 根据值获取label
  MatchLabelByAllObject: function (mapping, val, labelKey = 'label') {
    for (const item in mapping) {
      console.log(mapping[item]);
      if (String(mapping[item].value) === String(val)) {
        console.log(mapping[item][labelKey]);
        return mapping[item][labelKey];
      }
    }
    return '-';
  },
  // 根据值获取label
  MatchLabel: function (mapping, val, labelKey = 'label') {
    for (const item in mapping) {
      if (String(mapping[item].value) === String(val)) {
        return mapping[item][labelKey];
      }
    }
    return '-';
  },
  // 根据值获取object
  MatchObject: function (mapping, val) {
    for (const item in mapping) {
      if (String(mapping[item].value) === String(val)) {
        return mapping[item];
      }
    }
    return {};
  },
  TransferOptions: (object) => {
    let options = [];
    for (const o in object) {
      if (object[o].optionHidden) continue;
      options.push(object[o]);
    }
    return options;
  },
  ValueEqMapping: (mapping, val) => {
    if (typeof (mapping) === 'object') {
      return String(mapping.value) === String(val);
    }
    return String(mapping) === String(val);
  }
};

export {
  ResourceMapping,
  ApplicationMapping,
  UserMapping,
  MappingTools
};
