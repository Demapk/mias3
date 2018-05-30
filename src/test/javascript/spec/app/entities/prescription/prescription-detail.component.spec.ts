/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { PrescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/prescription/prescription-detail.component';
import { PrescriptionService } from '../../../../../../main/webapp/app/entities/prescription/prescription.service';
import { Prescription } from '../../../../../../main/webapp/app/entities/prescription/prescription.model';

describe('Component Tests', () => {

    describe('Prescription Management Detail Component', () => {
        let comp: PrescriptionDetailComponent;
        let fixture: ComponentFixture<PrescriptionDetailComponent>;
        let service: PrescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [PrescriptionDetailComponent],
                providers: [
                    PrescriptionService
                ]
            })
            .overrideTemplate(PrescriptionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrescriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Prescription(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.prescription).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
