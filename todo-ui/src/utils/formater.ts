export const DATE_TIME = 'DD.MM.YYYY HH:mm';

export const trimValue = (value: any): any => (value && typeof value === 'string' ? value.trim() : value);
