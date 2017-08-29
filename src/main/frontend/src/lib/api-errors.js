
export function handleApiErrors(response) {
  if (response.status >= 400 || response.error) {
    if (response.status === 403) {
      throw 'Expired session.';
    }
    throw response.message;
  }
  return response;
}
