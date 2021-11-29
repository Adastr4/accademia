import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ErrorComponentsPage, ErrorDeleteDialog, ErrorUpdatePage } from './error.page-object';

const expect = chai.expect;

describe('Error e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let errorComponentsPage: ErrorComponentsPage;
  let errorUpdatePage: ErrorUpdatePage;
  let errorDeleteDialog: ErrorDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Errors', async () => {
    await navBarPage.goToEntity('error');
    errorComponentsPage = new ErrorComponentsPage();
    await browser.wait(ec.visibilityOf(errorComponentsPage.title), 5000);
    expect(await errorComponentsPage.getTitle()).to.eq('demoneApp.error.home.title');
    await browser.wait(ec.or(ec.visibilityOf(errorComponentsPage.entities), ec.visibilityOf(errorComponentsPage.noResult)), 1000);
  });

  it('should load create Error page', async () => {
    await errorComponentsPage.clickOnCreateButton();
    errorUpdatePage = new ErrorUpdatePage();
    expect(await errorUpdatePage.getPageTitle()).to.eq('demoneApp.error.home.createOrEditLabel');
    await errorUpdatePage.cancel();
  });

  it('should create and save Errors', async () => {
    const nbButtonsBeforeCreate = await errorComponentsPage.countDeleteButtons();

    await errorComponentsPage.clickOnCreateButton();

    await promise.all([
      errorUpdatePage.setErroridInput('5'),
      errorUpdatePage.setDescriptionInput('description'),
      errorUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      errorUpdatePage.sourceSelectLastOption(),
    ]);

    await errorUpdatePage.save();
    expect(await errorUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await errorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Error', async () => {
    const nbButtonsBeforeDelete = await errorComponentsPage.countDeleteButtons();
    await errorComponentsPage.clickOnLastDeleteButton();

    errorDeleteDialog = new ErrorDeleteDialog();
    expect(await errorDeleteDialog.getDialogTitle()).to.eq('demoneApp.error.delete.question');
    await errorDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(errorComponentsPage.title), 5000);

    expect(await errorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
