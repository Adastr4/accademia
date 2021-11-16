import * as dayjs from 'dayjs';
import { ISource } from 'app/entities/source/source.model';

export interface IError {
  id?: number;
  errorid?: number | null;
  description?: string | null;
  data?: dayjs.Dayjs | null;
  source?: ISource | null;
}

export class Error implements IError {
  constructor(
    public id?: number,
    public errorid?: number | null,
    public description?: string | null,
    public data?: dayjs.Dayjs | null,
    public source?: ISource | null
  ) {}
}

export function getErrorIdentifier(error: IError): number | undefined {
  return error.id;
}
