import { OrsPage } from './app.po';

describe('ors App', function() {
  let page: OrsPage;

  beforeEach(() => {
    page = new OrsPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
