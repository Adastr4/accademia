import { element, by, ElementFinder } from 'protractor';

export class ErrorComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-error div table .btn-danger'));
  title = element.all(by.css('jhi-error div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ErrorUpdatePage {
  pageTitle = element(by.id('jhi-error-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  erroridInput = element(by.id('field_errorid'));
  descriptionInput = element(by.id('field_description'));
  dataInput = element(by.id('field_data'));

  sourceSelect = element(by.id('field_source'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setErroridInput(errorid: string): Promise<void> {
    await this.erroridInput.sendKeys(errorid);
  }

  async getErroridInput(): Promise<string> {
    return await this.erroridInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async sourceSelectLastOption(): Promise<void> {
    await this.sourceSelect.all(by.tagName('option')).last().click();
  }

  async sourceSelectOption(option: string): Promise<void> {
    await this.sourceSelect.sendKeys(option);
  }

  getSourceSelect(): ElementFinder {
    return this.sourceSelect;
  }

  async getSourceSelectedOption(): Promise<string> {
    return await this.sourceSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ErrorDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-error-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-error'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
