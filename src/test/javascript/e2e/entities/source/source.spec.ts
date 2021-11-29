import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SourceComponentsPage, SourceDeleteDialog, SourceUpdatePage } from './source.page-object';

const expect = chai.expect;

describe('Source e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sourceComponentsPage: SourceComponentsPage;
  let sourceUpdatePage: SourceUpdatePage;
  let sourceDeleteDialog: SourceDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Sources', async () => {
    await navBarPage.goToEntity('source');
    sourceComponentsPage = new SourceComponentsPage();
    await browser.wait(ec.visibilityOf(sourceComponentsPage.title), 5000);
    expect(await sourceComponentsPage.getTitle()).to.eq('demoneApp.source.home.title');
    await browser.wait(ec.or(ec.visibilityOf(sourceComponentsPage.entities), ec.visibilityOf(sourceComponentsPage.noResult)), 1000);
  });

  it('should load create Source page', async () => {
    await sourceComponentsPage.clickOnCreateButton();
    sourceUpdatePage = new SourceUpdatePage();
    expect(await sourceUpdatePage.getPageTitle()).to.eq('demoneApp.source.home.createOrEditLabel');
    await sourceUpdatePage.cancel();
  });

  it('should create and save Sources', async () => {
    const nbButtonsBeforeCreate = await sourceComponentsPage.countDeleteButtons();

    await sourceComponentsPage.clickOnCreateButton();

    await promise.all([
      sourceUpdatePage.setSourceidInput('sourceid'),
      sourceUpdatePage.setDescriptionInput('description'),
      sourceUpdatePage.fonteSelectLastOption(),
      sourceUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    await sourceUpdatePage.save();
    expect(await sourceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Source', async () => {
    const nbButtonsBeforeDelete = await sourceComponentsPage.countDeleteButtons();
    await sourceComponentsPage.clickOnLastDeleteButton();

    sourceDeleteDialog = new SourceDeleteDialog();
    expect(await sourceDeleteDialog.getDialogTitle()).to.eq('demoneApp.source.delete.question');
    await sourceDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(sourceComponentsPage.title), 5000);

    expect(await sourceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
