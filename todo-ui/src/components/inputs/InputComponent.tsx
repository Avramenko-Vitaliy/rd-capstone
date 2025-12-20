import { ChangeEvent, forwardRef, memo, SyntheticEvent } from 'react';
import TextField, { TextFieldProps } from '@mui/material/TextField';
import FormControl from '@mui/material/FormControl';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import { withStyles } from 'tss-react/mui';
import ClearIcon from '@mui/icons-material/Clear';

export type InputComponentProps = Omit<TextFieldProps<'outlined'>, 'error'> & {
  noPadding?: boolean;
  clearable?: boolean;
  error?: boolean | string;
  classes?: Partial<Record<keyof ReturnType<typeof styles>, string>>;
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
  onClear?: (e: SyntheticEvent) => void;
};

const styles = (_, props: InputComponentProps, classes: Partial<Record<keyof ReturnType<typeof styles>, string>>): any => ({
  defaultCss: {
    '& .MuiInputBase-root': {
      display: 'flex',
      paddingRight: props.clearable ? 30 : 0,
    },
    '& input': {
      textOverflow: 'ellipsis',
    },
    [`&:hover .${classes['clear']}`]: {
      display: 'inherit',
    },
    [`&:focus .${classes['clear']}`]: {
      display: 'inherit',
    },
  },
  label: {
    marginBottom: 8,
  },
  clear: {
    display: 'none',
  },
  adornment: {
    right: 7,
    position: 'absolute',
  },
});

function InputComponent(props: InputComponentProps, ref)  {
  const {
    clearable,
    label,
    error,
    className,
    classes,
    value,
    multiline,
    helperText,
    fullWidth,
    slotProps = {},
    onClear,
    ...other
  } = props;
  // @ts-expect-error by some reason can't recognize
  const actualValue = value || slotProps?.htmlInput?.value || slotProps?.input?.value;
  const hasValue = !!(actualValue?.length && actualValue !== 'All');
  let endAdornment = null;
  if (clearable) {
    // @ts-expect-error by some reason can't recognize
    endAdornment = hasValue && !props.disabled && !props.slotProps?.input?.readOnly && (
      <IconButton
        size="small"
        type="button"
        className={classes?.['clear']}
        onClick={onClear}
      >
        <ClearIcon />
      </IconButton>
    );
  }

  const assignedSlotProps = {
    ...slotProps,
    input: {
      ...(slotProps?.input || {}),
      endAdornment: (
        <InputAdornment position="end" className={classes?.['adornment']}>
          {endAdornment}
          {/* @ts-expect-error by some reason can't recognize */}
          {slotProps?.input?.endAdornment}
        </InputAdornment>
      ),
    },
  };
  return (
    <FormControl fullWidth={fullWidth} className={className}>
      {label && (
        <Typography
          component="label"
          htmlFor={other.name}
          className={classes?.['label']}
          color={other.disabled ? 'textDisabled' : 'textPrimary'}
        >
          {label}
        </Typography>
      )}
      <TextField
        id={other.name}
        error={!!error}
        helperText={helperText}
        className={classes?.['defaultCss']}
        value={value || ''}
        multiline={multiline}
        slotProps={assignedSlotProps}
        {...other}
        ref={ref}
        inputRef={ref}
      />
    </FormControl>
  );
}

export default memo(withStyles(forwardRef(InputComponent), styles));
