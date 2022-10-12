const clipboardCopy = (text: string) => {
  navigator.clipboard.writeText(text);
};

export default clipboardCopy;
