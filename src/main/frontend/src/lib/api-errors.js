// because Fetch doesn't recognize error responses as
// actual errors since it's technically completing the response...

export function handleApiErrors(response) {
  if (response.status >= 400 || response.error) {
    throw response.message;
  }
  return response;
}
